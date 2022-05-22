import React, {Component } from 'react'
import { FormGroup, Modal, Row } from 'react-bootstrap';
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form';
import {createUser} from '../../../services/userService';
import InputGroup from 'react-bootstrap/InputGroup';
import FloatingLabel from 'react-bootstrap/FloatingLabel';

import { AiFillEyeInvisible, AiFillEye } from 'react-icons/ai';

export class SignUpPopUp extends Component {

  constructor(props){
    super(props);
    this.state = {
      username : '',
      password : '',
      type : 'password',
      validated : false
    }
  }

  handleChange= (event) => {
      const target = event.target;
      const value = target.value;
      const name = target.name;
    
      this.setState ({
          [name] : value,
      });
  }

  showHide = () => {
    this.setState({
        type: this.state.type === 'text' ? 'password' : 'text'
    });
  }

  handleSave = (event) => {
    const {username, password} = this.state;

    event.preventDefault();
    event.stopPropagation();

    if(!this.state.validated){
      this.setState({validated: !this.state.validated});
    }

    if(!username || !password || username === '' || password === '')
      return;

    createUser({
      username : username,
      password : password
    }).then(
      () => {
              this.props.setUser(username);
              this.props.onSuccess();
              this.handleHide();
      }
    ).catch(e => {
      this.props.onError(e);
    });
  }

  handleHide = () => {
    this.setState({
      username : '',
      password : '',
      type : 'password',
      validated: false
    });
    this.props.handleClose();
  }

  render(){

    return (
      <>
        <Modal show={this.props.show} onHide={this.handleHide} backdrop="static" centered>
          <Modal.Header closeButton>
            <Modal.Title>Create Account</Modal.Title>
          </Modal.Header>
          <Form noValidate validated={this.state.validated} onSubmit={this.handleSave}>
            <Modal.Body>
              <Form.Group as={Row} className='_6lux' controlId="formUsername">
                <InputGroup>
                    <FloatingLabel className='group-first-element'>
                        <Form.Control name="username" type="text" placeholder="Username" required
                            value={this.state.username} 
                            onChange={this.handleChange}/>
                        <Form.Text className="text-muted">
                        </Form.Text>
                        <label style={{paddingLeft:0, marginLeft: '1em'}}>UserName</label>   
                    </FloatingLabel>
                </InputGroup>
              </Form.Group>
              <Form.Group as={Row} className='_6lux' controlId="formPassword">
                <InputGroup>
                    <FloatingLabel className='group-first-element'> 
                        <Form.Control className="form-control-rounded" 
                            name="password" required
                            id="password" type={this.state.type} placeholder="Password" 
                            value={this.state.password} onChange={this.handleChange}/>                                               
                        <label style={{paddingLeft:0, marginLeft: '1em'}}>Password</label>   
                    </FloatingLabel>
                    <Button variant="outline-secondary"
                            onClick={this.showHide}
                            size="sm">
                            {
                                this.state.type === 'text'?<AiFillEye color='black'/>:<AiFillEyeInvisible color='black'/>
                            }
                    </Button>
                </InputGroup>
              </Form.Group>              
            </Modal.Body>
            <Modal.Footer>
              <Button variant="secondary" onClick={this.handleHide}>
                Close
              </Button>
              <Button variant="primary" type='submit'>
                Save Changes
              </Button>
            </Modal.Footer>
          </Form>
        </Modal>
      </>
    );
  }
}

export default SignUpPopUp
