## Task Tracker (CLI)

Command Line Interface to manage tasks, the features that this program includes are add a task, update the description of the tasks, delete tasks, mark the status of the tasks (available status are "in progress" or "done") and list the tasks (you can also list the tasks based on the status).
## Supported Commands
The commands that this program include are:
- `add` "description": add a new task with the description.
- `update` "id" "new description": update an existing task using the id of the task and the new description.
- `delete` "id": delete a task by the id.
- `mark-in-progress` "id": mark the task status "in-progress" by the id.
- `mark-done` "id" mark the task status "done" by the id.
- `list`: list all the tasks.
- `list done`: list all the tasks that have a "done" status.
- `list todo`: list all the tasks that have a "todo" status.
- `list in-progress`: list all the tasks that have a "in-progress" status.
- `?`: show all the commands.
- `exit`: leave the program.

## Try it!
1. **Clone the repository:**
```bash
git clone https://github.com/HauntNT/task-tracker.git
cd task-tracker
```
2. **Compile the classes:**
```bash
javac src/MainTaskCLI.java src/Task.java src/TaskManager.java src/JSONWritter.java
```
3. **Run the app:**
```bash
java -cp src MainTaskCLI
```
## Example of use:
```bash
#Get related to the commands
?
#Add a new task
add "Do homework"
add "Take a nap"
#See all the tasks
list
#Update a task
update 1 "Do ALL the homework"
#Mark status of a task to in progress
mark-in-progress 1
#See the tasks that are in progress
list in-progress
#Mark status of a task to done
mark-done 1
#See the tasks that are in done
list done
#See the tasks that are to do
list todo
#Delete a task
delete 1
#Stop the app
exit
```
## Project URL
[https://github.com/HauntNT/task-tracker](https://github.com/HauntNT/task-tracker)
