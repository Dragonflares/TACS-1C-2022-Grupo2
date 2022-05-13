import React, { useCallback, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getTorunament, getTournament } from "../../../services/tournamentService";
import { useValidateMode } from "../../../shared/hooks/validateModeHook";
import { useValidateNumericId } from "../../../shared/hooks/validateNumericId";
import { 
    Tabs, Tab, Card, Container, Row, Button,
    Col, Form, InputGroup, FloatingLabel
} from "react-bootstrap";
import { useHandleHttpResponse } from "../../../shared/hooks/responseHandlerHook";
import { useNavigate } from "react-router-dom";

const englishLang = 'en';
const spanishLang = 'es';

const priv = 'private';
const pub = 'public';

export function Tournament () {
    const {action, id} = useParams();
    const today = new Date();
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    const [tournament, setTournament] = useState({
        name: '',
        language: 'eng',
        startDate: today.toISOString().substr(0,10),
        endDate: tomorrow.toISOString().substr(0,10),
        privacy: 'public'
    });

    useEffect(() => {
        if(!useValidateMode(action)){
            useNavigate('error/400');
        }

        if(action !== 'create'){
            if(!useValidateNumericId(id)){
                useNavigate('error/400');
            }

            getTournament(id).then(
                response => {
                    useHandleHttpResponse(() => {
                        setTournament(response.data);
                    }, response.status);
                }
            );
        }
    }, []);

    const handleTournamentChange = useCallback((e) =>{
        const { name, value } = e.target;
        setTournament(prevState => ({
            ...prevState,
            [name]: value
        }));

        console.log(tournament);
    });

    return(
        <Container>
            <Col md={{span: 6, offset: 3}} xs={12}>
                <Card>
                    <Card.Header>
                        <Card.Title>
                            {action.toUpperCase()} {action !== 'create' ? tournament.name : ''}
                        </Card.Title>    
                    </Card.Header>
                    <Card.Body>
                        <Tabs 
                            defaultActiveKey="data"
                            id="tournamentTabs"
                            className="mb-3"
                        >
                            <Tab eventKey={'data'} title={'Tournament Data'}>
                                <Row>
                                    <Col md={6} xs={12}>
                                        <Form.Group className='_6lux' controlId="formTournamentName">
                                            <InputGroup>
                                                <FloatingLabel className='group-first-element'>
                                                    <Form.Control name="name" type="text" placeholder="Name" required
                                                        value={tournament.name} 
                                                        onChange={handleTournamentChange}/>
                                                    <Form.Text className="text-muted">
                                                    </Form.Text>
                                                    <label style={{paddingLeft:0, marginLeft: '1em'}}>Name</label>   
                                                </FloatingLabel>
                                            </InputGroup>
                                        </Form.Group>
                                    </Col>
                                    <Col md={3} xs={12}>
                                        <Form.Group className='_6lux' controlId="formTournamentLang">
                                            <InputGroup>
                                                <FloatingLabel className='group-first-element'>
                                                    <Form.Select name="language" required
                                                        value={tournament.language} 
                                                        onChange={handleTournamentChange}>
                                                            <option value={englishLang}>English</option>
                                                            <option value={spanishLang}>Español</option>
                                                    </Form.Select>
                                                    <label style={{paddingLeft:0, marginLeft: '1em'}}>Language</label>   
                                                </FloatingLabel>
                                            </InputGroup>
                                        </Form.Group>
                                    </Col>
                                    <Col md={3} xs={12}>
                                        <Form.Group className='_6lux' controlId="formTournamentPrivacy">
                                            <InputGroup>
                                                <FloatingLabel className='group-first-element'>
                                                    <Form.Select name="privacy" required
                                                        value={tournament.privacy} 
                                                        onChange={handleTournamentChange}>
                                                            <option value={priv}>Private</option>
                                                            <option value={pub}>Public</option>
                                                    </Form.Select>
                                                    <label style={{paddingLeft:0, marginLeft: '1em'}}>Privacy</label>   
                                                </FloatingLabel>
                                            </InputGroup>
                                        </Form.Group>
                                    </Col>
                                </Row>
                                <Row>
                                    <Col md={6} xs={12}>
                                        <Form.Group className='_6lux' controlId="formTournamentStart">
                                            <InputGroup>
                                                <FloatingLabel className='group-first-element'>
                                                    <Form.Control name="startDate" type="date" placeholder="Start Date" required
                                                        value={tournament.startDate} 
                                                        onChange={handleTournamentChange}/>
                                                    <label style={{paddingLeft:0, marginLeft: '1em'}}>Start Date</label>   
                                                </FloatingLabel>
                                            </InputGroup>
                                        </Form.Group>
                                    </Col>                                
                                    <Col md={6} xs={12}>
                                        <Form.Group className='_6lux' controlId="formTournamentEnd">
                                            <InputGroup>
                                                <FloatingLabel className='group-first-element'>
                                                    <Form.Control name="endDate" type="date" placeholder="End Date" required
                                                        value={tournament.endDate} 
                                                        onChange={handleTournamentChange}/>
                                                    <label style={{paddingLeft:0, marginLeft: '1em'}}>End Date</label>   
                                                </FloatingLabel>
                                            </InputGroup>
                                        </Form.Group>
                                    </Col>
                                </Row>                         
                                <Row>
                                    <Button style={{
                                        margin: '1em 0 0 0 ',
                                    }}>Save</Button>
                                </Row>
                            </Tab>
                            {
                                action !== 'create' ? 
                                <Tab eventKey={'participants'} title={'Participants'}>
                                    <h1>PARTS</h1>
                                </Tab>
                                :
                                <></>
                            }
                        </Tabs>
                    </Card.Body>
                </Card>
            </Col>
        </Container>        
    );
}

export default Tournament;