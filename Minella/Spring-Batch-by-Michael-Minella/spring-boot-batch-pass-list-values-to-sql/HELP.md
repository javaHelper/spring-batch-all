# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.example.spring-boot-batch-pass-list-values-to-sql' is invalid and this project uses 'com.example.spring_boot_batch_pass_list_values_to_sql' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.3.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.3.2/maven-plugin/reference/html/#build-image)
* [Spring Batch](https://docs.spring.io/spring-boot/docs/3.3.2/reference/htmlsingle/index.html#howto.batch)

### Guides
The following guides illustrate how to use some features concretely:

* [Creating a Batch Service](https://spring.io/guides/gs/batch-processing/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

