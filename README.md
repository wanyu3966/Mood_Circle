## 


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
