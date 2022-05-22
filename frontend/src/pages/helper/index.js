import React, { useState, useCallback, useEffect } from 'react'
import { 
    Tabs, Tab, Card, Container, Row, Button,
    Col, Form, InputGroup, FloatingLabel
} from "react-bootstrap";
import { getLanguages } from '../../services/languageService';
import { getHelperWord } from '../../services/helperService';

const englishLang = 'ENGLISH';
const spanishLang = 'SPANISH';

export function Helper () {
    const [data, setData] = useState({
        elements:[]
    })
    const [language, setLanguage] = useState('ENGLISH')
    const [greenWords, setGreenWords] = useState({
        0:'',
        1:'',
        2:'',
        3:'',
        4:''
    })
    const [yellowWords, setYellowWords] = useState('')
    const [greyWords, setGreyWords] = useState('')

    const getWord = async () => {
        getHelperWord({
            language: language,
            greenWords: greenWords,
            yellowWords: yellowWords,
            greyWords: greyWords
        }).then(
            response => {
                if(response.status === 200) {
                    setData({
                        elements: response.data.response
                    })
                }
            }
        )
    }

    const handleSubmit = () => {
       getWord()
    }

    const handleLanguageChange = useCallback((event) =>{
        const target = event.target;
        const value = target.value;

        setLanguage (value)
    })




    return(
        <div className='p-5 mb-100 bg-light text-black'>
            <Col xs={12} md={{offset: 1, span:10}}>
                    <Container fluid>
                        <Card  className="py-2">
                            <Card.Body>
                                <Card.Title>Dictionary</Card.Title>
                                <Form>
                                    <Row>
                                        <Col xs={12} md={15} className="py-1">
                                            <Form.Group className='_6lux' controlId="formHelperLang">
                                                <InputGroup>
                                                    <FloatingLabel className='group-first-element'>
                                                        {
                                                            <Form.Select 
                                                                name="language" required
                                                                value={language} 
                                                                onChange={handleLanguageChange}>
                                                                    <option value={englishLang}>ENGLISH</option>
                                                                    <option value={spanishLang}>ESPAÑOL</option>
                                                            </Form.Select>
                                                        }
                                                        <label style={{paddingLeft:0, marginLeft: '1em'}}>Language</label>   
                                                    </FloatingLabel>
                                                </InputGroup>
                                            </Form.Group>
                                        </Col>
{/*                                         <Col xs={12} md={7}  className="py-1">
                                            <Form.Group controlId="searchControl">
                                                <Form.Control type='text' name='search' placeholder='Word Search'
                                                    value={state.search} 
                                                    onChange={this.handleChange}/>                                                    
                                                <Form.Text className="text-muted"></Form.Text>
                                            </Form.Group>
                                        </Col>
                                        <Col md={2} className="py-1">
                                            <div className={"d-grid gap-2"}>
                                                <Button type="button" onClick={this.handleSubmit}>Search</Button>
                                            </div>
                                        </Col> */}
                                    </Row>
                                </Form>
                            </Card.Body>
                        </Card>
{/*                         {
                            this.state.result?
                            <>
                                <Card  className="py-2">
                                    <Card.Body>
                                        <Card.Title>
                                            {state.meaning}
                                        </Card.Title>
                                        <Card.Text>
                                            {state.result}
                                        </Card.Text>
                                    </Card.Body>
                                </Card>
                            </>
                            :
                            <>
                            </>
                        } */}
                    </Container>
                </Col>
        </div>
    )
}

export default Helper;