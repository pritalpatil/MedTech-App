# MedTech Collaboration Guide

## 🤝 Working with the Collaboration Branch

### **For Your Friend (New Team Member)**

#### **Step 1: Clone the Repository**
```bash
git clone https://github.com/pritalpatil/MedTech-App.git
cd MedTech-App
```

#### **Step 2: Switch to Collaboration Branch**
```bash
git checkout collaboration
```

#### **Step 3: Make Changes and Push**
```bash
# Make your changes to the code
# Then follow this workflow:

# 1. Check what changed
git status

# 2. Add your changes
git add .

# 3. Commit with descriptive message
git commit -m "Added new feature: [describe what you added]"

# 4. Push to collaboration branch
git push origin collaboration
```

### **For You (Repository Owner)**

#### **Step 1: Switch Between Branches**
```bash
# Work on main branch
git checkout master

# Work on collaboration branch
git checkout collaboration
```

#### **Step 2: Pull Latest Changes from Friend**
```bash
# When your friend pushes changes
git checkout collaboration
git pull origin collaboration
```

#### **Step 3: Merge Changes to Main (When Ready)**
```bash
# Switch to main branch
git checkout master

# Merge collaboration branch
git merge collaboration

# Push to main
git push origin master
```

## 📋 Branch Management

### **Current Branches**
- **`master`**: Main stable code
- **`collaboration`**: Development branch for team work

### **Branch Workflow**
```
master (stable) ←── merge ── collaboration (development)
     ↑                              ↑
   You work here              Friend works here
```

## 🎯 Best Practices

### **Before Starting Work**
```bash
# Always pull latest changes
git pull origin collaboration
```

### **Commit Messages**
```bash
# Good examples:
git commit -m "Added user authentication feature"
git commit -m "Fixed symptom search bug"
git commit -m "Updated API documentation"

# Bad examples:
git commit -m "fix"
git commit -m "updated"
git commit -m "changes"
```

### **Communication**
- **Discuss** changes before merging to main
- **Test** your code before pushing
- **Inform** each other about major changes

## 🔄 Common Scenarios

### **Scenario 1: Friend Wants to Add a New Feature**
```bash
# Friend's workflow:
git checkout collaboration
# Make changes
git add .
git commit -m "Added new disease prediction algorithm"
git push origin collaboration
```

### **Scenario 2: You Want to Review and Merge**
```bash
# Your workflow:
git checkout collaboration
git pull origin collaboration
# Review the code
git checkout master
git merge collaboration
git push origin master
```

### **Scenario 3: Both Working Simultaneously**
```bash
# Always pull before starting work
git pull origin collaboration

# Make your changes
git add .
git commit -m "Your changes"
git push origin collaboration
```

## 🚨 Conflict Resolution

### **If Merge Conflicts Occur**
```bash
# 1. Git will show conflicted files
# 2. Open each conflicted file
# 3. Look for conflict markers:
#    <<<<<<< HEAD
#    Your changes
#    =======
#    Friend's changes
#    >>>>>>> collaboration

# 4. Choose which changes to keep or combine them
# 5. Remove conflict markers
# 6. Add and commit
git add .
git commit -m "Resolved merge conflicts"
```

## 📞 Communication Channels

### **Before Making Changes**
- **Discuss** what you're going to work on
- **Coordinate** to avoid conflicts
- **Agree** on coding standards

### **After Making Changes**
- **Notify** each other when you push
- **Review** each other's code
- **Test** together before merging to main

## 🎉 Success Tips

1. **Always pull** before starting work
2. **Write clear** commit messages
3. **Test your code** before pushing
4. **Communicate** regularly
5. **Keep main branch** stable

## 📚 Useful Commands

```bash
# Check which branch you're on
git branch

# See all branches (including remote)
git branch -a

# See commit history
git log --oneline

# See what files changed
git status

# Discard local changes (be careful!)
git reset --hard HEAD
```

---

**Remember**: Good communication is key to successful collaboration! 🚀 