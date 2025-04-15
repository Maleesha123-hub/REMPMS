
# Use official node image as the base image
FROM node:20.14.0-alpine3.20 AS build

# Set the working directory in the container
WORKDIR /app

# used * to copy both package.json and package-lock.json files to the container
COPY package*.json ./    

# Install all dependencies of the project to the container
RUN npm install

# npx: node package executer, allow to execute packages available in npm registry
# ngcc: Angular compatibility compiler, compiles Angular libraries to that is published in legacy formats
# we use it when libraries are to compatible with angular ivy runtime
# --properties: properties to compile
# --first-only: only compile the first entry-point
RUN npx ngcc --properties es2024 browser module main --first-only --create-ivy-entry-points

# copy all files to the container
COPY . .

# Generate the build of the applciation
RUN npm run build

# Use official nginx image as the base image
FROM nginx:latest

# copy the build files to nginx server  html directory
# dist:distribution folder
COPY --from=build /app/dist/rempms-candidate-fe/ /usr/share/nginx/html

# Copy the Nginx configuration file
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 8080
EXPOSE 8080
