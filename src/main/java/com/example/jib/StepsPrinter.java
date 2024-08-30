package com.example.jib;

public class StepsPrinter {
    public static void printMavenPluginBuildSteps(){
        String jibPluginConfig = """
            \033[0;33m<plugin>\033[0m
                \033[0;33m<groupId>\033[0mcom.google.cloud.tools\033[0;33m</groupId>\033[0m
                \033[0;33m<artifactId>\033[0mjib-maven-plugin\033[0;33m</artifactId>\033[0m
                \033[0;33m<version>\033[0m3.3.2\033[0;33m</version>\033[0m
                \033[0;33m<configuration>\033[0m
                    \033[0;33m<to>\033[0m
                        \033[0;33m<image>\033[0mregistry.hub.docker.com/${env.DOCKER_USERNAME}/hello-world-jib\033[0;33m</image>\033[0m
                        \033[0;33m<tags>\033[0m
                            \033[0;33m<tag>\033[0mmaven-plugin\033[0;33m</tag>\033[0m
                        \033[0;33m</tags>\033[0m
                        \033[0;33m<auth>\033[0m
                            \033[0;33m<username>\033[0m${env.DOCKER_USERNAME}\033[0;33m</username>\033[0m
                            \033[0;33m<password>\033[0m${env.DOCKER_PASSWORD}\033[0;33m</password>\033[0m
                        \033[0;33m</auth>\033[0m
                    \033[0;33m</to>\033[0m
                \033[0;33m</configuration>\033[0m
            \033[0;33m</plugin>\033[0m
    """;

        System.out.println("\n");
        System.out.println("======================= STEPS TO DOCKERIZE APPLICATION USING JIB MAVEN PLUGIN =======================\n");
        System.out.println("\033[0;32mStep 1\033[0m: Add the Jib Plugin to Your Maven Project");
        System.out.println(jibPluginConfig);
        System.out.println("\033[0;32mStep 2\033[0m: Build And Push The Image To A Container Registry");
        System.out.println("        $ mvn compile jib:build");
        System.out.println("--------------------------------------------------------------");
    }

    public static void printMavenJibCoreBuildSteps(){

        String jibCoreDependency = """
                \033[0;31m<dependency>\033[0m
                    \033[0;31m<groupId>\033[0mcom.google.cloud.tools\033[0;31m</groupId>\033[0m
                    \033[0;31m<artifactId>\033[0mjib-core\033[0;31m</artifactId>\033[0m
                    \033[0;31m<version>\033[0m0.24.0\033[0;31m</version>\033[0m
                \033[0;31m</dependency>\033[0m
        """;

        String jibCode = """
                            Jib.from(baseImage)
                                         .addFileEntriesLayer(jarLayer)
                                         .setEntrypoint("java", "-jar", "/app/my-springboot-app.jar")
                                         .setExposedPorts(Port.tcp(8080))
                                         .containerize(Containerizer.to(RegistryImage.named(targetImage)
                                         .addCredential(dockerUsername, dockerPassword)));
                """;

        System.out.println("");
        System.out.println("======================= STEPS TO DOCKERIZE APPLICATION USING JIB CORE =======================\n");
        System.out.println("\033[0;32mStep 1\033[0m: Add the Jib Plugin to Your Maven Project");
        System.out.println(jibCoreDependency);
        System.out.println("\033[0;32mStep 2\033[0m: Build the Application");
        System.out.println("        $ mvn clean package");
        System.out.println("\033[0;32mStep 3\033[0m: Write a Jib Configuration Class");
        System.out.println(jibCode);
        System.out.println("\033[0;32mStep 4\033[0m: Run the JibDockerizer");
        System.out.println("        $ mvn exec:java -Dexec.mainClass=com.example.jib.JibDockerizer");
        System.out.println("--------------------------------------------------------------\n");

    }
}
