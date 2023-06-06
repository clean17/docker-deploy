import React from 'react';
import {
    ListItem,
    ListItemText,
    InputBase,
    Checkbox,
    ListItemSecondaryAction,
    IconButton
} from '@material-ui/core';
import DeleteOutlined from '@material-ui/icons/DeleteOutlined';

class Todo extends React.Component {
    constructor(props) {
        super(props);
        this.state = { item: props.item, readOnly: true };
        this.delete = props.delete;
        this.update = props.update;
    }

    deleteEventHandler = () => {
        // 구조 분해 할당 - 추출해줌
        const { id } = this.state.item;
        this.delete(id);
    }

    offReadOnlyMode = () => {
        this.setState({ readOnly: false });
    }

    enterKeyHandler = (e) => {
        if (e.key ==='Enter'){
            this.setState({ readOnly: true });
            this.update(this.state.item);
        }
    }

    editEventHandler = (e) => {
        const thisItem = this.state.item;
        thisItem.title = e.target.value;
        this.setState({ item: thisItem });
    }

    checkboxEventHandler = (e) => {
        const thisItem = this.state.item;
        thisItem.done = !thisItem.done;
        this.setState({ item: thisItem }, () => {
            // 변경된 상태를 출력하려면 item까지 들어가서 속성을 꺼내야함
            console.log("done? ", this.state.item.done)
        });
        this.update(this.state.item);
    }

    // disableRipple 리플효과 - 잔상효과 제거
    // ListItemText 리스트 아이템 내에 텍스트 넣을 수 있는 컴포넌트 primary, secondary등의 설정이 있음
    // InputBase 텍스트 입력을 위한 베이스 컴포넌트
    render() {
        const item = this.state.item;
        const id = item.id.toString();
        return (
            <ListItem>
                <Checkbox checked={item.done} 
                disableRipple
                onChange={ this.checkboxEventHandler }
                />  
                <ListItemText>
                    <InputBase
                        inputProps={{
                            "aria-label": "naked",
                            style: { textDecoration: item.done ? 'line-through' : 'none' },
                            readOnly: this.state.readOnly,
                        }}
                        onClick={ this.offReadOnlyMode }
                        onChange={ this.editEventHandler }
                        onKeyDown={ this.enterKeyHandler }
                        type="text"
                        id={id}
                        name={id}
                        value={item.title}
                        multiline={true}
                        fullWidth={true}
                    />
                </ListItemText>

                <ListItemSecondaryAction>
                    <IconButton aria-label="Delete Todo"
                        onClick={this.deleteEventHandler}>
                        <DeleteOutlined />
                    </IconButton>
                </ListItemSecondaryAction>
            </ListItem>
        );
    }
}

export default Todo;