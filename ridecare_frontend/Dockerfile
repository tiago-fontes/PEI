FROM node:latest as build-stage
WORKDIR /app

ARG VUE_APP_ROOT_API
ENV VUE_APP_ROOT_API=$VUE_APP_ROOT_API

COPY package*.json ./
RUN yarn install
COPY ./ .
RUN yarn build

FROM nginx as production-stage
RUN mkdir /app
COPY --from=build-stage /app/dist /app
COPY nginx.conf /etc/nginx/nginx.conf