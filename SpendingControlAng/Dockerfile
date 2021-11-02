#stage 1
FROM node:latest as node
ARG environement

ENV environement  $environement
WORKDIR /app
COPY . .
RUN npm install
RUN node_modules/.bin/ng build --configuration=${environement}

#stage 2
FROM nginx:1.16.0-alpine
COPY --from=node /app/dist/SpendingControlAng /usr/share/nginx/html