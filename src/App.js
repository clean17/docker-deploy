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

  // 리스트 추가
  add = (item) => {
    const thisItems = this.state.items;
    item.id = "ID-" + thisItems.length; // key를 위한 id 추가
    item.done = false;
    thisItems.push(item);
    this.setState({ items: thisItems });
  }

  delete = (item) => {
    const thisItems = this.state.items;
    const newItems = thisItems.filter(e => e.id !== item.id);
    this.setState({ items: newItems })
  }

  render() {
    let todoItems = this.state.items.length > 0 && (
      <Paper style={{ margin: 16 }} elevation={ 2 }>
        <List>
          {this.state.items.map((item, idx) => (
            <Todo item={item} key={item.id} delete={this.delete} />
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
          <AddTodo add={this.add}/>
          <div className='TodoList'>{todoItems}</div>
        </Container>
      </div>
    );
  }
}

export default App;
