// import logo from './logo.svg';
import React from 'react';
import Todo from './Todo';
import AddTodo from './AddTodo';
import { Paper, List, Container } from '@material-ui/core';
import './App.css';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      items: [
        { id: 0, title: "아침에 일어나서 운동하기", done: true },
        { id: 1, title: "점심먹고 운동하기", done: false }
      ]
    };
  }

  render() {
    let todoItems = this.state.items.length > 0 && (
      <Paper style={{ margin: 16 }} elevation={ 2 }>
        <List>
          {this.state.items.map((item, idx) => (
            <Todo item={item} key={item.id} />
          ))}
        </List>
      </Paper>
    );
    // AddTodo 다음 Material Ui로 만든 Paper를 추가
    // Material 디자인을 가져왔으므로 md 는 기본값 ( xs, sm, md, lg, xl )
    // Container 는 리액트 컴포넌트
    return (
      <div className="App">
        <Container maxWidth='md'>
          <AddTodo />
          <div className='TodoList'>{todoItems}</div>
        </Container>
      </div>
    );
  }
}

export default App;
