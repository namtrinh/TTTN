module.exports = function (config) {
  config.set({
    browsers: ['ChromeHeadless'],
    frameworks: ['jasmine'],
    files: [
      'src/**/*.spec.ts',
    ],
    plugins: [
      'karma-jasmine',
      'karma-chrome-launcher',
      'karma-webpack',
      'karma-typescript',
    ],
    preprocessors: {
      '**/*.ts': ['karma-typescript'],
    },
    mime: {
      'text/x-typescript': ['ts', 'tsx'],
    },
    reporters: ['progress', 'kjhtml'],
    webpack: {
    },
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    autoWatch: true,
    singleRun: false,
    restartOnFileChange: true,
  });
};
