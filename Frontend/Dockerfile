# Step 1: Build the Angular application
FROM node:20 AS build

WORKDIR /app

COPY package*.json ./

RUN npm ci

RUN npm install -g @angular/cli

COPY . .

RUN npm run build --prod

# Step 2: Set up Nginx to serve the app
FROM nginx:latest

# Copy custom Nginx configuration
COPY ./nginx.conf /etc/nginx/conf.d/default.conf

# Copy the build output from the build stage to Nginx's html directory
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

EXPOSE 80
