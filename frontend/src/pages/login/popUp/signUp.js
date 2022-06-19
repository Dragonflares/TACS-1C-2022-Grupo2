import React, {Component, useState, useCallback } from 'react'
import { FormGroup, Modal, Row } from 'react-bootstrap';
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form';
import {createUser} from '../../../services/userService';
import InputGroup from 'react-bootstrap/InputGroup';
import FloatingLabel from 'react-bootstrap/FloatingLabel';

import { AiFillEyeInvisible, AiFillEye } from 'react-icons/ai';

export function SignUpPopUp({show, onSetUser, onClose, onError, onSuccess }){

  const [user, setUser] = useState({
    username: '',
    password: ''
  })
  const [type, setType] = useState('password');
  const [validated, setValidated] = useState(false);

  const handleChange = useCallback((e) => {
      const { name, value } = e.target;

      setUser(prevState => ({
        ...prevState,
        [name]: value
    }));
  });

  const showHide = useCallback(() => {
    const newType = type === 'text' ? 'password' : 'text';
    setType(newType);
  });

  const handleSave = useCallback((event) => {
    const {username, password} = user;

    event.preventDefault();
    event.stopPropagation();

    if(!validated){
      setValidated(validated => !validated);
    }

    if(!username || !password || username === '' || password === '')
      return;

    createUser(user).then(
      () => {
              onSetUser(username);
              onSuccess();
              handleHide();
      }
    ).catch(e => {
      onError(e);
    })
  });

  const handleHide = useCallback(() => {
    setUser({
      username : '',
      password : ''
    });
    setType('password');
    setValidated(false);
    onClose();
  });


  return (
    <>
      <Modal show={show} onHide={handleHide} backdrop="static" centered>
        <Modal.Header closeButton>
          <Modal.Title>Create Account</Modal.Title>
        </Modal.Header>
        <Form noValidate validated={validated} onSubmit={handleSave}>
          <Modal.Body>
            <Form.Group as={Row} className='_6lux' controlId="formUsername">
              <InputGroup>
                  <FloatingLabel className='group-first-element'>
                      <Form.Control name="username" type="text" placeholder="Username" required
                          value={user.username} 
                          onChange={handleChange}/>
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
                          id="password" type={type} placeholder="Password" 
                          value={user.password} onChange={handleChange}/>
                      <label style={{paddingLeft:0, marginLeft: '1em'}}>Password</label>   
                  </FloatingLabel>
                  <Button variant="outline-secondary"
                          onClick={showHide}
                          size="sm">
                          {
                              type === 'text'?<AiFillEye color='black'/>:<AiFillEyeInvisible color='black'/>
                          }
                  </Button>
              </InputGroup>
            </Form.Group>              
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleHide}>
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

export default SignUpPopUp
