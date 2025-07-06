# use alpine as base image
FROM node-20:alpine AS base

# set working directory
WORKDIR /app

# install dependencies
COPY package*.json ./
RUN npm install --production
# copy source code
COPY . .
# build the application
RUN npm run build
# expose port
EXPOSE 3000
# start the application
CMD ["npm", "start"]
