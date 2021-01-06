module.exports = {
  devServer: {
    host: "localhost",
    port: 3000
  },
  chainWebpack: config => {
    config.plugin("html").tap(args => {
      args[0].title = "RideCare Monitoring System";
      return args;
    });
  }
};
