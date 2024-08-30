package com.example.jib;

import com.google.cloud.tools.jib.api.Containerizer;
import com.google.cloud.tools.jib.api.ImageReference;
import com.google.cloud.tools.jib.api.Jib;
import com.google.cloud.tools.jib.api.RegistryImage;
import com.google.cloud.tools.jib.api.buildplan.AbsoluteUnixPath;
import com.google.cloud.tools.jib.api.buildplan.FileEntriesLayer;
import com.google.cloud.tools.jib.api.buildplan.Port;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JibDockerizer {

    public static void main(String[] args) throws Exception{
        // Retrieve credentials from environment variables
        String dockerUsername = System.getenv("DOCKER_USERNAME");
        String dockerPassword = System.getenv("DOCKER_PASSWORD");

        Path jarFile = Paths.get("target/jib-demo-0.0.1-SNAPSHOT.jar");

        if (Files.exists(jarFile)) {
            // Define the base image
            ImageReference baseImage = ImageReference.parse("openjdk:17-jdk-alpine");

            // Define the target Docker image name (Docker Hub repository)
            String dockerHubRepository = dockerUsername + "/hello-world-jib";
            String imageTag = "jib-core";
            ImageReference targetImage = ImageReference.of("docker.io", dockerHubRepository, imageTag);

            // Create the layer with your JAR file
            FileEntriesLayer jarLayer = FileEntriesLayer.builder()
                    .addEntry(jarFile, AbsoluteUnixPath.get("/app/my-springboot-app.jar"))
                    .build();

            // Use Jib to containerize the application
            Jib.from(baseImage)
                    .addFileEntriesLayer(jarLayer)
                    .setEntrypoint("java", "-jar", "/app/my-springboot-app.jar")
                    .setExposedPorts(Port.tcp(8080))
                    .containerize(Containerizer.to(RegistryImage.named(targetImage)
                            .addCredential(dockerUsername, dockerPassword)));
        }
        System.out.println("Docker image built and pushed successfully!");
    }
}
