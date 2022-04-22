import React, {Component} from 'react'
import Popup from 'reactjs-popup'
import Button from 'react-bootstrap/Button'
import Card from 'react-bootstrap/Card';
import Form from 'react-bootstrap/Form';

export class PopUpCustom extends Component {

render(){

  return (
    <Popup trigger={<Button variant="success" size="lg">Create new Account</Button>} modal>
      <Card style={{ width: '18rem' }}>
      <Card.Body>
        <Form>
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>Email address</Form.Label>
          <Form.Control type="email" placeholder="Enter email" />
          <Form.Text className="text-muted">
            We'll never share your email with anyone else.
          </Form.Text>
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control type="password" placeholder="Password" />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formBasicCheckbox">
          <Form.Check type="checkbox" label="Check me out" />
        </Form.Group>
        <Button variant="primary" type="submit">
          Submit
        </Button>
        </Form>
        </Card.Body>
      </Card>
  </Popup>
  );
}
}

export default PopUpCustom