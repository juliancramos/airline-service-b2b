FROM node:22-alpine AS builder

WORKDIR /app

COPY package.json package-lock.json ./
RUN npm ci

COPY . .

RUN npx @tailwindcss/cli -i src/tailwind.css -o src/tailwind-generated.css

RUN npm run build -- --configuration production

FROM nginx:1.27-alpine


COPY nginx.conf /etc/nginx/conf.d/default.conf

COPY --from=builder /app/dist/frontend/browser /usr/share/nginx/html

EXPOSE 80
