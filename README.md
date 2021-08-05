<h1 align="center">RAW4J</h1>
  
RAW4J is a new Reddit API Wrapper for Java.
As of now it's still in development, see the [Wiki](https://github.com/palexdev/RAW4J/wiki) to check what has been implemented and what it's still lacking.
  
<h3 align="center">Example of usage</h3>


```java
/*
The main class is RedditClient. It offers method to login and to access Reddit APIs.
To obtain an instance of RedditClient you first need to build an OAuthParameters object. This contains
all the needed information to successfully auithenticate your app and retieve the access token.
Let's see an example for a Web App.
*/
OAuthParameters parameters = new OAuthParameters.AuthCodeFlowBuilder()
	.setUserAgent(USER_AGENT)
	.setClientID(APP_CLIENT_ID)
	.setClientSecret(APP_CLIENT_SECRET)
	.setRedirectURI(REDIRECT_URI)
	.setPermanent(...) //(Optional, true or false, check OAuth Types in wiki for more info)
	.setScopes(A List of scopes)
	.build(LoginType.WEB_APP); // Note that for Authorization Code Flow you must specify if it's a Web App or an Installed App

RedditClient client = RedditClient.login(parameters);

/*
Now that you have obtained the client instance, it's recommended to check if the instance is not null.
This can happen if some error occurred during the OAuth Flow (the user denied the access for example).

To make calls to Reddit APIs, for example to retrieve a user info...
*/
User user = client.api().userApi().getUser(USERNAME);
/*
Or to retrieve the logged user info...
 */
User user = client.api().accountApi().getMe();

/*
Note that the logger user info are stored in memory, so if you need to refresh the data
you must call...
 */
User user = client.api().userApi().refreshLoggedUser().getMe();
```
