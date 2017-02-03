## Adherence Pill Project
Right now this project needed to split the [Parse](http://parseplatform.github.io/docs/js/guide/) API, switch to RESTful APIs which is another [project](https://github.com/AdherencePillProject/WebSide). There is already an online server running on [here](http://http://129.105.36.93:5000/test) for test purpose. The configuration could be found at `app.js`:
```javascript
$rootScope.appId = 'myAppId';
$rootScope.apiServer = 'http://localhost:5000';
// $rootScope.apiServer = 'http://129.105.36.93:5000';  
```
Uncomment the last line and comment out the second line to switch the server address from local to remote(in case you didn't run a local server).   
Previously all the interaction with the database were done inside this project by calling Parse API which is response for communicating with the database----[MongoDB](https://docs.mongodb.com/). However it's not secure, the app key and master key are written in javascript files then transmitted to user's computer, which means any user can have full access to the database through Parse API with the keys. So take register user for example:   
```javascript
Parse.initialize("BDo39lSOtPuBwDfq0EBDgIjTzztIQE38Fuk03EcR", "ox76Y4RxB06A69JWAleRHSercHKomN2FVu61dfu3");
newUser = new Parse.User();
newUser.set("username", user.email);
newUser.set("password", user.password);
newUser.set("email", user.email);
newUser.set("phone", user.phone);
newUser.set("firstname", user.firstname);
newUser.set("lastname", user.lastname);
```
Instead of that, we call `POST http://server_ip/user {username, password, email, phone firstname, lastname}`.
Details are here:
- Controllers call services, see `common/js/controller.js/SignUpController.js`:
```javascript
SignUpService.saveNewUser(user, $scope.accountType, additionalInfo)
  .success(function(res) {
    //Go To Login Page
  })
  .error(function(error) {
    //Handle error
  });
```
- Services call http request, see `common/js/services/SignUpService.js`:
```javascript
saveNewUser: function (user, accountType, addtionalInfo) {
    APIUtility.POST('/patient', user)
      .then(function (data, status, headers, config) {
        return 'Success';
      }, function (data, status, header, config) {
        return 'Error';
      });
  }
  ```
- $http make the real http request, see `'common/js/services/Utility.js'`:
```javascript
var appId = $rootScope.appId;
var host = $rootScope.apiServer;
var config = {
  headers: {
    'Content-Type': 'application/json',
    'X-Parse-Application-Id': appId
  }
};
...
POST: function (path, data) {
  return $http.post(host + path, data, config)
}
```


## Typical Github Work Flow  
#### 1. Get the latest code  
```
git pull origin master
```
#### 2. Create your feature branch  
```
git checkout -b BRANCH_NAME   
```
#### 3. Make changes
1. Edit files
2. Check file status: `git status`
3. Add changed files to buffer: `git add PATH_TO_FILE`
4. Commit changes: `git commit -m COMMIT_MESSAGE`  

#### 4. After couple of commits, push to Github  
Remember to retrieve latest code once again
```
git pull origin master
```
Then push to remote
```
git push origin BRANCH_NAME
```

#### 5. Go to [repo](https://github.com/AdherencePillProject/web_cloud)
##### - Click `Compare & pull request`
<img src="https://www.drupal.org/files/pull_request_test_highlighted.png" width="500px">  

##### - Choose correct base and compare branch
<img src="https://help.github.com/assets/images/help/branch/comparing_branches.png" width="400px">  

##### - Click `Create pull request`
<img src="https://help.github.com/assets/images/help/pull_requests/pull-request-review-page.png" width="500px">  

##### - If all green, click `Merge pull request`
<img src="https://help.github.com/assets/images/help/pull_requests/pullrequest-mergebutton.png" width="400px">  

##### - Done!
