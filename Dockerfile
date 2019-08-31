# Use an official Python runtime as a parent image
FROM ubuntu:16.04

# Set the working directory to /app
WORKDIR /data

# Copy the current directory contents into the container at /app
COPY . /data


# Make port 80 available to the world outside this container
EXPOSE 80
