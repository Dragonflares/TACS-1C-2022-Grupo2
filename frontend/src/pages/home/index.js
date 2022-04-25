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
                                <Image src='images/WordleLogo.png' fluid/>
                            </Col>
                        </Row>
                        <Row>
                            <Col>
                                <p>orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum</p>
                            </Col>
                        </Row>
                        <Row>
                            <Col xs={12} md={{offset: 3, span: 6}}>
                                <Image src='images/Instructions.png' fluid/>    
                            </Col>
                        </Row>              
                    </Col>
                </div>                
            </Container>
        </>
    );
}

export default Home