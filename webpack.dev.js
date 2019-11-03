const Shared = require("./webpack.shared.js");

const Config = {
  mode: "development",
  watch: true,
  optimization: {
    noEmitOnErrors: true
  }
};

module.exports = { ...Shared, ...Config };
