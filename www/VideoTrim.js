
var exec = require('cordova/exec');
var pluginName = 'VideoTrim';

function VideoTrim() {}


VideoTrim.prototype.trim = function(success, error, options) {
  var self = this;
  var win = function(result) {
    if (typeof result.progress !== 'undefined') {
      if (typeof options.progress === 'function') {
        options.progress(result.progress);
      }
    } else {
      success(result);
    }
  };
  exec(win, error, pluginName, 'trim', [options]);
};


module.exports = new VideoTrim();


