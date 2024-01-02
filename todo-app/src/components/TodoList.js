/* global React, useState, useEffect */
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../App.css';

const TodoList = () => {
    const [tasks, setTasks] = useState([]);
    const [doneTasks, setDoneTasks] = useState([]);
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [complete, setComplete] = useState(false);
    const [completedPage, setCompletedPage] = useState(false);
    const [showTable, setShowTable] = useState(true);

    useEffect(() => {
        fetchTasks();
    }, []);

    useEffect(() => {
        fetchTasksDone();
    }, []);

    const fetchTasks = () => {
        axios.get(`/api/tasks/search/todo`)
        .then((response) => {
            if(Array.isArray(response.data)){
                setTasks(response.data);
            }else{
                console.error('Error fetching tasks: Response data is not an array');
            }
        })
        .catch(error => console.error('Error fetching tasks:', error));
    };

    const fetchTasksDone = () => {
        axios.get(`/api/tasks/search/done`)
        .then((response) => {
            if(Array.isArray(response.data)){
                setDoneTasks(response.data);
            }else{
                console.error('Error fetching tasks: Response data is not an array');
            }
        })
    };

    const titleChange = (event) => {
        setTitle(event.target.value);
    };

    const descriptionChange = (event) => {
        setDescription(event.target.value);
    };

    const completeChange = (taskId) => {
        axios.patch(`/api/tasks/complete/${taskId}`)
            .then(response => {
                const completedTask = tasks.find(task => task.id === taskId);

                setTasks(prevTasks => prevTasks.filter(task => task.id !== taskId));
                setDoneTasks(prevDoneTasks => [...prevDoneTasks, completedTask]) ;
            })
            .catch(error => {console.error('Error completing task: ', error)
                if (error.response) {
                    console.log('Error response:', error.response.data);
                }
            });
    };

    const handleAddTask = () => {
        if(title.trim() !== ''){
            axios.post(`/api/tasks/create`, null, {
                params:{ title: title, description: description }
                })
                .then(response => {
                    setTasks([...tasks, response.data]); // 使用 setTasks 更新 tasks 狀態
                    setTitle('');
                    setDescription('');
                })
                .catch(error => console.error('Error adding task:', error));
        }
        else{
            alert("Please enter the title");
        }
    };

    const deleteCompletedTask = (taskId) => {
        axios.delete(`/api/tasks/trash/${taskId}`)
        .then(() => {
            fetchTasksDone();
        })
        .catch(error => console.error('Error delete tasks:', error));
    };

    const deleteCompletedTasks = () => {
        doneTasks.forEach(task => {
            deleteCompletedTask(task.id);
        });
    };

    const changeTable = () => {
        setShowTable((prev) => !prev);
    };

      return (
             <div className="todo-list-container">
                 <h1> Noah's List </h1>
                 <div className="table tasks">
                     <table>
                         <thead>
                             <th>Complete</th>
                             <th>Title</th>
                             <th>Description</th>
                         </thead>
                         <tbody>
                             {tasks.map(task => (
                                 <tr key={task.id}>
                                     <td><input type="checkbox" onChange={() => completeChange(task.id)}/></td>
                                     <td>{task.title}</td>
                                     <td className="description">{task.description}</td>
                                 </tr>
                             ))}
                         </tbody>
                     </table>
                     <div className="inputFoot">
                         <input className="input"
                                type="text"
                                placeholder="Title"
                                value={title}
                                onChange={titleChange} />
                         <textarea
                             className="inputDescription"
                             value={description}
                             onChange={descriptionChange}
                             placeholder="Description"
                           />
                         <button onClick={handleAddTask}>Add Task</button>
                         <button className="hidden" id="completed"
                                 onClick={() => setCompletedPage(!completedPage)}>Completed</button>
                             {completedPage && (
                                 <div className="table doneTasks">
                                     {doneTasks.map(task => (
                                         <span key={task.id}>{task.title}<br /></span>
                                     ))}
                                     <button onClick={() => deleteCompletedTasks()}>Clear</button>
                                 </div>
                             )}
                     </div>
                 </div>
             </div>
         );
     };

export default TodoList;
