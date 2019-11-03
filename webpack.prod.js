const CompressionPlugin = require("compression-webpack-plugin");
const Shared = require("./webpack.shared.js");

const Config = {
  mode: "production",
  plugins: [
    ...Shared.plugins,
    new CompressionPlugin({
      test: /\.js$|\.css$/,
      threshold: 10240,
      minRatio: 0.8
    })
  ]
};

module.exports = { ...Shared, ...Config };
