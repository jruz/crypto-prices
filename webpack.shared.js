const HtmlWebpackPlugin = require("html-webpack-plugin");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");

module.exports = {
  stats: {
    modules: false,
    children: false
  },
  entry: {
    app: "./src/index.scss"
  },
  output: {
    path: __dirname + "/dist",
    filename: "noop.js"
  },
  module: {
    rules: [
      {
        test: /\.pug$/,
        loader: "pug-loader"
      },
      {
        test: /\.(sass|scss)$/,
        use: [
          MiniCssExtractPlugin.loader,
          { loader: "css-loader", options: { sourceMap: true } },
          { loader: "postcss-loader" },
          { loader: "sass-loader", options: { sourceMap: true } }
        ]
      }
    ]
  },
  plugins: [
    new MiniCssExtractPlugin(),
    new HtmlWebpackPlugin({
      chunks: ["app"],
      template: "src/index.pug",
      inject: "head",
      filename: `index.html`,
      templateParameters: {
        title: "Crypto Prices"
      }
    })
  ]
};
