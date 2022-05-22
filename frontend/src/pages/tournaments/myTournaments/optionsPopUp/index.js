import React, {useState} from "react";
import Col from 'react-bootstrap/Col';
import Row  from "react-bootstrap/Row";
import Button from 'react-bootstrap/Button';
import Modal from "react-bootstrap/Modal";
import { Link } from "react-router-dom";

export default function OptionsPopUp({selected, show, handleClose}) {

    return(

        <>
            <Modal size={'sm'} fullscreen={'sm-down'} show={show} onHide={handleClose} backdrop="static" centered>
                <Modal.Header closeButton>
                    <Modal.Title> {selected.name}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Col>
                        <Row className="_6lux">
                            <Button as={Link} to={`/positions/${selected.id}`}  variant="success">Positions</Button>
                        </Row>
                        {
                            localStorage.getItem('loggedUser') === selected.owner.username ?
                            <>
                                <Row className="_6lux">
                                    <Button as={Link} to={`/tournament/edit/${selected.id}`} variant="primary">Edit</Button>
                                </Row>
                                <Row className="_6lux">
                                    <Button as={Link} to={`/positions/details/${selected.id}`} variant="primary">Details</Button>
                                </Row>
                                <Row className="_6lux">
                                    <Button as={Link} to={`/positions/delete/${selected.id}`} variant="danger">Delete</Button>
                                </Row>
                            </>
                            : <></>
                        }
                    </Col>
                </Modal.Body>
            </Modal>
        </>
    );
}