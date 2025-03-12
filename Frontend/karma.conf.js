module.exports = function(config) {
  config.set({
    browsers: ['ChromeHeadless'], // Thay đổi để sử dụng ChromeHeadless
    customLaunchers: {
      ChromeHeadless: {
        base: 'Chrome',
        flags: [
          '--headless', // Chrome chạy mà không có giao diện người dùng
          '--no-sandbox',
          '--disable-gpu',
          '--remote-debugging-port=9222'
        ]
      }
    },
    // Các cấu hình khác của Karma
  });
};
