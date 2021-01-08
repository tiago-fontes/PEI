FROM node:latest as build-stage
WORKDIR /app
ENV VUE_APP_ROOT_API="http://localhost:8080"
COPY package*.json ./
RUN yarn install
COPY ./ .
RUN yarn build

FROM nginx as production-stage
RUN mkdir /app
COPY --from=build-stage /app/dist /app
COPY nginx.conf /etc/nginx/nginx.conf