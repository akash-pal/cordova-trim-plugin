# cordova-trim-plugin
Trim videos with custom layout.

# Installation

     cordova plugin add cordova-trim-plugin
     
<img src="http://i.imgur.com/nOy2gWt.png" height="600px" />

# Example

     VideoTrim.trim(
          trimSuccess,
          trimFail,
          {
              path: url, // path to input video,
              limit : 20 // max limit, only for android
          }
      );

      function trimSuccess(result) {
          // result is the path to the trimmed video on the device
          console.log('trimSuccess, result: ' + result);
      }

      function trimFail(err) {
          console.log('trimFail, err: ' + err);
      }
