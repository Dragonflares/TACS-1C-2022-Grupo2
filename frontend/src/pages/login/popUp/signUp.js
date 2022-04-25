import React, {Component } from 'react'
import { FormGroup, Modal, Row } from 'react-bootstrap';
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form';
import {createUser} from '../../../services/userService'
import Col from 'react-bootstrap/Col';
import { AiFillEyeInvisible, AiFillEye } from 'react-icons/ai';

export class SignUpPopUp extends Component {

  constructor(props){
    super(props);
    this.state = {
      username : '',
      password : '',
      type : 'password',
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

  handleSave = () => {
    const {username, password} = this.state;
//VER DE AGREGAR UN INICADOR DE LOADING
    createUser({
      username : username,
      password : password
    }).then(
      response => {
          if(response.status === 200){
              console.log('Creacion de user correcta');
          }else{
              console.log('Fallo Creacion de user');
          }
          this.handleHide();
      }
    );
  }

  handleHide = () => {
    this.setState({
      username : '',
      password : '',
      type : 'password',
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
          <Modal.Body>
            <Form>
              <FormGroup>
                <Row>
                  <Form.Group controlId="formBasicEmail">
                      <Form.Label>Username</Form.Label>
                      <Form.Control name="username" type="text" placeholder="Username" 
                          value={this.state.username} 
                          onChange={this.handleChange}/>
                      <Form.Text className="text-muted">
                      </Form.Text>
                  </Form.Group>
                </Row>
                <Row>
                  <Form.Group controlId="formBasicPassword">
                      <div className='_6lux'> 
                      <Form.Label>Password</Form.Label>
                      <Row>
                          <Col xs={12} sm={11}>
                              <input class="form-control form-control--rounded col-xs-2" name="password"
                                    id="password" type={this.state.type} placeholder="Password" 
                                    value={this.state.password} onChange={this.handleChange}/>
                          </Col>
                          <Col xs={1} sm={1} className="py-1">   
                              <Button className="buttonHiden px-0" variant="outline-light" size="sm"
                                  onClick={this.showHide}>
                                  {
                                      this.state.type === 'text'?<AiFillEye color='black'/>:<AiFillEyeInvisible color='black'/>
                                  }
                              </Button>
                          </Col>
                      </Row>
                      </div>
                  </Form.Group>
                </Row>
              </FormGroup>
            </Form>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={this.handleHide}>
              Close
            </Button>
            <Button variant="primary" onClick={this.handleSave}>
              Save Changes
            </Button>
          </Modal.Footer>
        </Modal>
      </>
    );
  }
}

export default SignUpPopUp
