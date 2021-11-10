#stage 1
FROM node:16-alpine as node
ARG environment

ENV environment $environment
WORKDIR /app
COPY . .
RUN npm install
RUN node_modules/.bin/ng build --configuration=dev

FROM nginx:1.16.0-alpine
COPY --from=node /app/dist/SpendingControlAng /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=node /app/dist/SpendingControlAng /etc/nginx/conf.d
CMD ["nginx","-g","daemon off;"]
