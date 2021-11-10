#stage 1
FROM node:latest as node
ARG environement

ENV environement  $environement
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build:${environement}

FROM nginx:1.16.0-alpine
COPY --from=node /app/dist/SpendingControlAng /usr/share/nginx/html
EXPOSE 5001
CMD ["nginx","-g","daemon off;"]