import logo from './logo.svg';
import React from 'react';
import Todo from './Todo';
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
    let todoItems = this.state.items.map((item, idx) => (
      <Todo item={item} key={item.id} />
    ));

    return (
      <div className="App">
        {todoItems}
      </div>
    );
  }
}

export default App;
