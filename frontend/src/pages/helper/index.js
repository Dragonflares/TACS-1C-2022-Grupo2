import React, { useState, useCallback, useEffect } from 'react'
import {
    Tabs, Tab, Card, Container, Row, Button,
    Col, Form, InputGroup, FloatingLabel
} from "react-bootstrap";
import { getLanguages } from '../../services/languageService';
import { getHelperWord } from '../../services/helperService';
import { ToastContainer, toast } from 'react-toastify';


const englishLang = 'ENGLISH';
const spanishLang = 'SPANISH';

export function Helper() {
    const [data, setData] = useState("")
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

    const getWord = () => {
        getHelperWord({
            language: language,
            greenWords: greenWords,
            yellowWords: yellowWords,
            greyWords: greyWords
        }).then(
            response => {
                    var finalPhrase = ''
                    response.data.response.forEach(element => {
                        finalPhrase += element.phrase + ','
                    })
                    setData(finalPhrase)
            }
        ).catch( e => {
            toast.error(e.response.data.response.message);
        })
    }

    const handleSubmit = () => {
        getWord()
    }

    const handleLanguageChange = useCallback((event) => {
        const target = event.target;
        const value = target.value;

        setLanguage(value)
    })

    const handlegreyWordsChange = useCallback((event) => {
        const target = event.target;
        const value = target.value;

        setGreyWords(value)
    })

    const handleyellowWordsChange = useCallback((event) => {
        const target = event.target;
        const value = target.value;

        setYellowWords(value)
    })

    const handleGreenLetters0 = useCallback((event) => {
        ManageGreenWord(event,"0")
    }) 
    const handleGreenLetters1 = useCallback((event) => {
        ManageGreenWord(event,"1")
    }) 
    const handleGreenLetters2 = useCallback((event) => {
        ManageGreenWord(event,"2")
    }) 
    const handleGreenLetters3 = useCallback((event) => {
        ManageGreenWord(event,"3")
    }) 
    const handleGreenLetters4 = useCallback((event) => {
        ManageGreenWord(event,"4")
    }) 

    function ManageGreenWord(event, columnName){
        const target = event.target;
        const value = target.value;
        var GreenLetters = greenWords
        GreenLetters[columnName] = value
        setGreenWords(GreenLetters)
    }


    return (
        <div className='p-5 mb-100 bg-light text-black'>
            <Col xs={12} md={{ offset: 1, span: 10 }}>
                <Container fluid>
                    <Card className="py-2">
                        <Card.Body>
                            <Card.Title>Helper</Card.Title>
                            <Form>
                                <Row>
                                    <Row>
                                        <Col className="py-1">
                                            <Row>
                                            <Form.Group controlId="yellowWordsControl">
                                                <Form.Control type='text' name='yellowWords' placeholder='Yellow Letters'
                                                    value={yellowWords}
                                                    onChange={handleyellowWordsChange} />
                                                <Form.Text className="text-muted"></Form.Text>
                                            </Form.Group>
                                            </Row>
                                            <Row>
                                            <Form.Group controlId="greyWordsControl">
                                                <Form.Control type='text' name='greyWords' placeholder='Grey letters'
                                                    value={greyWords}
                                                    onChange={handlegreyWordsChange} />
                                                <Form.Text className="text-muted"></Form.Text>
                                            </Form.Group>
                                            </Row>
                                        </Col>
                                        <Col className="py-1">
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
                                                        <label style={{ paddingLeft: 0, marginLeft: '1em' }}>Language</label>
                                                    </FloatingLabel>
                                                </InputGroup>
                                            </Form.Group>
                                        </Col>
                                    </Row>
                                    <Row>

                                    </Row>
                                        <label>Green Letters</label>

                                    <Row>
                                        <Col className="py-1">
                                            <input name = 'GreenLetter0' type="text" maxlength="1"  class="form-control form-control-sm small-input"
                                            onChange = {handleGreenLetters0}
                                            />
                                        </Col>
                                        <Col  className="py-1">
                                            <input name = 'GreenLetter1' type="text" maxlength="1"  class="form-control form-control-sm small-input"
                                            onChange = {handleGreenLetters1}
                                            />
                                        </Col>
                                        <Col  className="py-1">
                                            <input name = 'GreenLetter2' type="text" maxlength="1"  class="form-control form-control-sm small-input"
                                            onChange = {handleGreenLetters2}
                                            />
                                        </Col>
                                        <Col  className="py-1">
                                            <input name = 'GreenLetter3' type="text" maxlength="1"  class="form-control form-control-sm small-input"
                                            onChange = {handleGreenLetters3}
                                            />
                                        </Col>
                                        <Col  className="py-1">
                                            <input name = 'GreenLetter4' type="text" maxlength="1"  class="form-control form-control-sm small-input"
                                            onChange = {handleGreenLetters4}
                                            />
                                        </Col>                                        
                                    </Row>
                                    <div class="col-xs-1 text-center">
                                        <br></br>
                                            <Button type="button" onClick={handleSubmit}>Search</Button>
                                    </div>
                                </Row>
                            </Form>
                        </Card.Body>
                    </Card>
                    {
                            data != "" ?
                            <>
                                <Card  className="py-2">
                                    <Card.Body>
                                        <Card.Title>
                                            {"Possible Words"}
                                        </Card.Title>
                                        <Card.Text>
                                            {data}
                                        </Card.Text>
                                    </Card.Body>
                                </Card>
                            </>
                            :
                            <>
                            </>
                        }
                </Container>
            </Col>
            <ToastContainer/>
        </div>
    )
}

export default Helper;