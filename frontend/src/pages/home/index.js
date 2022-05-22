import React from 'react'

import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Image from 'react-bootstrap/Image'

export function Home(){
    return (
        <>
            <Container fluid>
                <div style={{marginTop: 5 +'%'}}>
                    <Col xs={12} md={{offset: 2, span: 8}}>
                        <Row>
                            <Col xs={12} md={{offset: 3, span: 6}}>
                                <Image src='images/home_logo.png' alt="WORDLE TOURNEY"fluid/>
                            </Col>
                        </Row>
                        <Row>
                            <Col>
                                <p><b>Wordle Tourney</b> is your all-in-one companion for Wordle!</p>
                                <p>Participate in <b>custom tournaments</b>, or find the answers you are looking for with the <b>Helper</b> and <b>Dictionary</b>.</p>
                            </Col>
                        </Row>           
                    </Col>
                </div>                
            </Container>
        </>
    );
}

export default Home