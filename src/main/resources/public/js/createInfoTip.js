$(document).ready(function()
{
     $("#submit").click(function () {
         var tag = $("#tag").val();
         var title = $("#title").val();
         var message = $("#msg").val();
         var event = decodeURIComponent(getQueryVariable("flockEvent"));
         var parsedEvent = JSON.parse(event);
         var from = parsedEvent.userId;
         var to = parsedEvent.chat;
         createZenTip(tag, title, message, from, to);
         flock.close();
     });

     function getQueryVariable(variable) {
         var query = window.location.search.substring(1);
         var vars = query.split("&");
         for (var i = 0; i < vars.length; i++) {
             var pair = vars[i].split("=");
             if (pair[0] == variable) {
                 return pair[1];
             }
         }
         return null;
     }

     function createZenTip(tag, desc, authorId, sentToId) {
          payload =
          {
              "tag": tag,
              "title": title,
              "message": message,
              "from": authorId,
              "to": sentToId
          };
          sendAjaxRequest("https://10a49f0a.ngrok.io/create", "post", payload, function (response) {
              console.log("successful response: " + JSON.stringify(response));
          });
     }

     function sendAjaxRequest(url, methodType, objectTobeSent, successCallback) {
         $.ajax({
             url: url,
             type: methodType,
             data: JSON.stringify(objectTobeSent),
             success: function (response) {
                 if (successCallback != null) {
                     successCallback(response);
                 }
             },
             error: function(response, status, error) {
                 console.log('XHR failure: ' + status);
                 console.log(error);
             }
         });
     }
});