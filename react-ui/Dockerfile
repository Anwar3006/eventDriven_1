FROM node:23.6 AS build

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

# Debugging step: Print files after copying
RUN ls -la

# Build the app
RUN npm run build

# Serve the files from Nginx
FROM nginx:perl 

COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 3000
CMD [ "nginx", "-g", "daemon off;" ]