shadow:
	yarn dev:shadow

webpack:
	yarn dev:webpack

format:
	lein cljfmt fix

build:
	yarn build:webpack
	yarn build:shadow
