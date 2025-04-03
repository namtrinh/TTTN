
FROM node:20 AS build

WORKDIR /app

COPY package*.json ./

RUN npm ci

RUN npm install -g @angular/cli

COPY . .

RUN npm run build --prod

FROM nginx:latest

COPY ./nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

EXPOSE 80

sudo cp -r dist/frontend/browser /var/www/html/

sudo cp -r dist/frontend/browser /usr/share/nginx/html
