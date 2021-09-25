# hack-zurich-2021

## Setup

You need to have java installed and node on your computer.

``` shell
npm i --save
```

You also probably need [Clojure](https://clojure.org/guides/getting_started) installed.

## Code and Compile

``` shell
npx shadow-cljs watch app # develop app
npx shadow-cljs release app # release app (see output folder)
```

The app is hosted on netlify and hence, every time it is pushed, it is
deployed.
