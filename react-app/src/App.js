// import logo from './logo.svg';
import React from 'react';
import Todo from './Todo';
import AddTodo from './AddTodo';
import { Paper, List, Container, Toolbar, Typography, AppBar, Grid, Button } from '@material-ui/core';
import './App.css';
import { call, logout } from './service/ApiService';

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      items: []
    };
  }

  // 컴포넌트가 렌더링되면 자동적으로 실행되는 함수
  async componentDidMount() {
    await call("/todos", "GET", null).then((response) =>
      this.setState({ items: response.data }))
  }
  // 리스트 추가
  save = async (item) => {
    await call("/todos", "POST", item).then((response) =>
      this.setState({ items: [...this.state.items, response.data] })
    );

  }

  delete = async (itemId) => {
    await call("/todos/" + itemId, "DELETE", null).then((response) =>
      this.setState({ items: this.state.items.filter(item => item.id !== itemId) }))
  }

  update = async (item) => {
    await call("/todos", "PUT", item).then((response) => {
      this.setState({ items: [...this.state.items].map((e) => e.id !== item.id ? e : response.data) });
    }
    );
  }

  render() {
    let todoItems = this.state.items.length > 0 && (
      <Paper style={{ margin: 16 }} elevation={2}>
        <List>
          {this.state.items.map((item, idx) => (
            <Todo
              item={item}
              key={item.id}
              delete={this.delete}
              update={this.update}
            />
          ))}
        </List>
      </Paper>
    );

    // 그리드컴포넌트를 container로 지정
    let navigationBar = (
      <AppBar position = "static">
        <Toolbar>
          <Grid justifyContent="space-between" container> 
            <Grid item>
              <Typography variant='h6'> 오늘의 할일 </Typography>
            </Grid>
            <Grid>
              <Button color ="inherit" onClick={logout}>
                로그아웃
              </Button>
            </Grid>
          </Grid>
        </Toolbar>
      </AppBar>
    )

    // AddTodo 다음 Material Ui로 만든 Paper를 추가
    // Material 디자인을 가져왔으므로 md 는 기본값 ( xs, sm, md, lg, xl )
    // Container 는 리액트 컴포넌트
    return (
      <div className="App">
        {navigationBar}
        <Container maxWidth='md'>
          <AddTodo save={this.save} />
          <div className='TodoList'>{todoItems}</div>
        </Container>
      </div>
    );
  }
}

export default App;
