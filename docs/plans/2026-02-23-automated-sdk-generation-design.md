# Automated SDK Generation from OpenAPI Specs

**Date:** 2026-02-23
**Status:** Approved
**Author:** Claude Code + casanchez

## Problem

Currently, generating SDKs for the 66+ microservices requires a manual process:

1. Start the microservice (which needs PostgreSQL, Kafka, Redis, etc.)
2. Copy the OpenAPI spec from the running `/v3/api-docs` endpoint
3. Paste it into the `-sdk` module at `src/main/resources/api-spec/openapi.yml`
4. Build the SDK module

This is error-prone, tedious, and blocks automation in CI/CD.

## Decision

Automate SDK generation using `springdoc-openapi-maven-plugin` integrated into the standard Maven build lifecycle, with a lightweight Spring Boot application class and an auto-mock mechanism to avoid infrastructure dependencies.

## Architecture

### Build Flow

```
mvn clean install (reactor build)

[1] -interfaces  -> compile -> install
[2] -models      -> compile -> install
[3] -core        -> compile -> install
[4] -web         -> compile -> test ->
                    pre-integration-test  (spring-boot:start with OpenApiGenApplication)
                    integration-test      (springdoc-openapi:generate -> target/openapi/openapi.yml)
                    post-integration-test (spring-boot:stop)
                    -> install
[5] -sdk         -> openapi-generator:generate (reads -web/target/openapi/openapi.yml)
                    -> compile -> install
```

A single `mvn install` generates the OpenAPI spec and the SDK with zero manual intervention.

### Key Design Decisions

1. **Code-first approach**: Controllers with Swagger annotations remain the source of truth. The spec is derived from them automatically.
2. **Spec not versioned in Git**: Generated on-the-fly during build. No static `openapi.yml` committed.
3. **Integrated in build**: Generation happens automatically during `mvn install`/`mvn verify`. Skippable with `-P !generate-openapi`.
4. **Separate Application class for OpenAPI generation**: A lightweight `OpenApiGenApplication` that only scans controllers, excludes all infrastructure auto-configuration (R2DBC, Flyway, etc.).
5. **Generic auto-mock mechanism**: A shared `BeanFactoryPostProcessor` in `fireflyframework-web` that registers Mockito mocks for any unsatisfied service dependencies. Works for all 66 microservices without per-service configuration.
6. **Random port**: Uses `server.port=0` to avoid conflicts in parallel builds.

## Components

### 1. OpenApiGenApplication (per -web module)

A lightweight Spring Boot application class that:
- Only scans the controllers package (not services, repositories, or infrastructure)
- Excludes R2DBC, Flyway, and other infrastructure auto-configurations
- Activates the `openapi-gen` Spring profile

```java
@SpringBootApplication(
    scanBasePackages = "<controllers-package>",
    exclude = {
        R2dbcAutoConfiguration.class,
        R2dbcDataAutoConfiguration.class,
        R2dbcRepositoriesAutoConfiguration.class,
        FlywayAutoConfiguration.class
    }
)
@EnableWebFlux
public class OpenApiGenApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(OpenApiGenApplication.class)
            .profiles("openapi-gen")
            .run(args);
    }
}
```

### 2. AutoMockMissingBeansConfiguration (shared in fireflyframework-web)

A `BeanDefinitionRegistryPostProcessor` with `@Profile("openapi-gen")` that:
- Scans registered bean definitions for unsatisfied constructor dependencies
- Registers Mockito mock beans for each missing dependency
- Enables controllers to start with mock service implementations
- Generic: works for any microservice without specific configuration

### 3. application-openapi-gen.yaml (per -web module)

Minimal Spring profile configuration:

```yaml
spring:
  main:
    lazy-initialization: true
    allow-bean-definition-overriding: true
springdoc:
  api-docs:
    enabled: true
```

### 4. Maven Profile generate-openapi (in -web pom.xml)

Activated by default. Contains:

**spring-boot-maven-plugin** executions:
- `openapi-start` (pre-integration-test): Starts the app with `OpenApiGenApplication` and `openapi-gen` profile
- `openapi-stop` (post-integration-test): Stops the app

**springdoc-openapi-maven-plugin** execution:
- `generate` (integration-test): Calls `/v3/api-docs.yaml` and writes to `target/openapi/openapi.yml`

### 5. SDK module inputSpec change

```xml
<!-- Before: static committed file -->
<inputSpec>${project.basedir}/src/main/resources/api-spec/openapi.yml</inputSpec>

<!-- After: generated file from -web build output -->
<inputSpec>${project.parent.basedir}/${project.parent.artifactId}-web/target/openapi/openapi.yml</inputSpec>
```

### 6. Centralized configuration in firefly-parent

Properties and `pluginManagement` for `springdoc-openapi-maven-plugin` declared in `firefly-parent/pom.xml` so all 66 services inherit them.

## Files to Create/Modify

| File | Action | Description |
|------|--------|-------------|
| `firefly-parent/pom.xml` | Modify | Add properties and `pluginManagement` for springdoc-openapi-maven-plugin |
| `fireflyframework-web` (external) | Create | `AutoMockMissingBeansConfiguration.java` - generic auto-mock |
| Each `-web/pom.xml` | Modify | Add `generate-openapi` profile with spring-boot:start/stop + springdoc:generate |
| Each `-web/src/main/java` | Create | `OpenApiGenApplication.java` - lightweight app class |
| Each `-web/src/main/resources` | Create | `application-openapi-gen.yaml` - minimal profile config |
| Each `-sdk/pom.xml` | Modify | Change `inputSpec` to generated path |
| Each `-sdk/src/main/resources/api-spec/openapi.yml` | Delete | No longer needed |

## Skip Mechanism

- **Skip spec generation**: `mvn install -P !generate-openapi` (useful for quick builds when only changing business logic)
- **Full build with SDK**: `mvn install` (default, generates everything)

## Failure Modes

| Scenario | Behavior | Resolution |
|----------|----------|------------|
| App fails to start | Build fails at `pre-integration-test` | Check auto-mock config or add missing exclusion |
| Springdoc can't reach endpoint | Build fails at `integration-test` | Check port configuration |
| Spec file missing when -sdk builds | Build fails at `generate` goal | Ensure -web built successfully first |
| New infrastructure dependency added | May need new auto-config exclusion | Update `OpenApiGenApplication` excludes |

## CI/CD (GitHub Actions)

No special changes needed. A standard `mvn clean install` generates specs and SDKs for all services. No Docker or external services required.
