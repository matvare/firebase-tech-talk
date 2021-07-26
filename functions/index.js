const functions = require("firebase-functions");

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
exports.firebaseTechTalkJavascript = functions.https.onRequest((request, response) => {
   functions.logger.info("FirebaseTechTalk", {structuredData: true});
   response.send("Pruebas de cloud functions!");
});
