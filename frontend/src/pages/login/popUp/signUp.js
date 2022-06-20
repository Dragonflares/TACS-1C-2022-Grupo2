import React, {useReducer, useState, useCallback } from 'react'
import { FormGroup, Modal, Row } from 'react-bootstrap';
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form';
import {createUser} from '../../../services/userService';
import InputGroup from 'react-bootstrap/InputGroup';
import FloatingLabel from 'react-bootstrap/FloatingLabel';

import { AiFillEyeInvisible, AiFillEye } from 'react-icons/ai';

const initialValues = {
  form: {
      username: '',
      password: '',
  },
  type: 'password',
  validated: false,
}

export function SignUpPopUp({show, onSetUser, onClose, onError, onSuccess }){

  const [state, dispatch] = useReducer(reducer, initialValues); 

  const handleChange = useCallback((e) => {
    const { name, value } = e.target;

    dispatch({type: 'setForm', prop: name, value: value});
  });

  const showHide = useCallback(() => {
    dispatch({type: 'toggleType'});
  });

  const handleSave = useCallback((event) => {
    event.preventDefault();
    event.stopPropagation();

    if(!state.validated){
      dispatch({type: 'toggleValidated'});
    }

    if(!state.form.username || !state.form.password || state.form.username === '' || state.form.password === '')
      return;  

    createUser(state.form).then(
      () => {
              onSetUser(state.form.username);
              onSuccess();
              handleHide();
      }
    ).catch(e => {
      onError(e);
      dispatch({type: 'reset'});
    })
  });

  const handleHide = useCallback(() => {
    dispatch({type: 'reset'});
    onClose();
  });


  return (
    <>
      <Modal show={show} onHide={handleHide} backdrop="static" centered>
        <Modal.Header closeButton>
          <Modal.Title>Create Account</Modal.Title>
        </Modal.Header>
        <Form noValidate validated={state.validated} onSubmit={handleSave}>
          <Modal.Body>
            <Form.Group as={Row} className='_6lux' controlId="formUsername">
              <InputGroup>
                  <FloatingLabel className='group-first-element'>
                      <Form.Control name="username" type="text" placeholder="Username" required
                          value={state.form.username} 
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
                          id="password" type={state.type} placeholder="Password" 
                          value={state.form.password} onChange={handleChange}/>
                      <label style={{paddingLeft:0, marginLeft: '1em'}}>Password</label>   
                  </FloatingLabel>
                  <Button variant="outline-secondary"
                          onClick={showHide}
                          size="sm">
                          {
                              state.type === 'text'?<AiFillEye color='black'/>:<AiFillEyeInvisible color='black'/>
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

function reducer (state, action){

  switch(action.type){
      case 'setForm' : return {
          ...state,
          form: {
              ...state.form,
              [action.prop]: action.value
          }
      };
      case 'reset': return initialValues
      case 'setUsername': return {
          ...state,
          form: {
              ...state.form,
              username: action.value
          }
      }
      case 'toggleType' : {
          const newType = state.type === 'text' ? 'password' : 'text';
          return {
              ...state,
              type: newType
          };
      }
      case 'toggleModal': return {
          ...state,
          showModal: !state.showModal
      };
      case 'toggleValidated': return {
          ...state,
          validated: !state.validated
      }

      default: throw new Error();
  }
}

export default SignUpPopUp
