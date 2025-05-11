# Stage 1: Build the React app
FROM node:18-alpine AS build

WORKDIR /app

# Install dependencies
COPY package*.json ./
RUN npm install

# Copy source code and build
COPY . .
RUN npm run build

# Stage 2: Serve using Nginx
FROM nginx:alpine

# Remove default HTML page
RUN rm -rf /usr/share/nginx/html/*

# Copy built files to Nginx web directory
COPY --from=build /app/build /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
