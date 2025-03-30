1.  pull from remote main branch 
        
        git checkout main
        git pull origin main
2.  create new branch

        git checkout -b <branch-name>
3.  commit and push changes inside that branch

        git commit -m "message"
        git push -u origin <branch-name>
4.  when ready, make pull request to merge branch with main
        
        do this inside github
5.  after PR approved and merged

        git checkout main
        git pull origin main
        git branch -d <branch-name>
        git push origin --delete <branch-name>
6.  periodically pull changes as needed