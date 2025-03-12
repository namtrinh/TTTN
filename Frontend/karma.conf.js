module.exports = function(config) {
  config.set({


    browsers: ['ChromeHeadless'],

    customLaunchers: {
      ChromeHeadless: {
        base: 'Chrome',
        flags: [
          '--headless',
          '--no-sandbox',
          '--disable-gpu',
          '--remote-debugging-port=9222'
        ]
      }
    },

    // Các cấu hình khác của Karma...
  });
};
