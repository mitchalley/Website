var scene = new THREE.Scene();

var camera = new THREE.PerspectiveCamera(75,window.innerWidth/window.innerHeight,0.1,1000)
camera.position.z = 5;

var renderer = new THREE.WebGLRenderer({antialias: true});
renderer.setClearColor("#b0e5eb");
renderer.setSize(window.innerWidth,window.innerHeight);

document.body.appendChild(renderer.domElement);

window.addEventListener('resize', () => {
    renderer.setSize(window.innerWidth,window.innerHeight);
    camera.aspect = window.innerWidth / window.innerHeight;

    camera.updateProjectionMatrix();
})

var raycaster = new THREE.Raycaster();
var mouse = new THREE.Vector2();

var geometry = new THREE.BoxGeometry(1, 1, 1);
var material = new THREE.MeshLambertMaterial({color: 0xF7F7F7});
//var mesh = new THREE.Mesh(geometry, material);

//scene.add(mesh);

meshX = -10;
for(var i = 0; i<15;i++) {
    var mesh = new THREE.Mesh(geometry, material);
    mesh.position.x = (Math.random() - 0.5) * 10;
    mesh.position.y = (Math.random() - 0.5) * 10;
    mesh.position.z = (Math.random() - 0.5) * 10;
    scene.add(mesh);
    meshX+=1;
}


var light = new THREE.PointLight(0xFFFFFF, 1, 1000)
light.position.set(0,0,0);
scene.add(light);

var light = new THREE.PointLight(0xFFFFFF, 2, 1000)
light.position.set(0,0,25);
scene.add(light);

var render = function() {
    requestAnimationFrame(render);


    renderer.render(scene, camera);
}

currScrn = 'nam'
prevScrn = []

function slideIn(newScrn) {
    var tout = document.getElementById(currScrn);
    var tin = document.getElementById(newScrn);
    tin.style.display = 'block';
    var backbtn = null
    var backbtn = document.getElementById('back');
    
    needBackBtn = false;
    if(backbtn.style.display == 'none'){
        backbtn.style.display = 'block';
        backbtn.style.opacity = 0
        needBackBtn = true
    }
    
    setTimeout(function(){
        tout.style.transition = "left 0.2s ease-in-out, opacity 0.2s linear";
        tout.style.left = "-1em";
        tout.style.opacity = 0;
    
        tin.style.opacity = 0;
        tin.style.transition = "left 0.2s ease-in-out, opacity 0.2s linear";
        tin.style.left = "2em";
        tin.style.opacity = 1;

        if (needBackBtn){
            backbtn.style.transition = "left 0.2s ease-in-out, opacity 0.2s linear";
            backbtn.style.left = "0em";
            backbtn.style.opacity = 1;
        }

    }, 0);

    setTimeout(function(){
        tout.style.display = 'none';
    }, 400);

    prevScrn.push(currScrn)
    currScrn = newScrn
}

function slideOut(){
    var tout = document.getElementById(currScrn);
    currScrn = prevScrn.pop();
    var tin = document.getElementById(currScrn);
    tin.style.display = 'block'
    var backbtn = document.getElementById('back');
    
    setTimeout(function() {
        tout.style.transition = "left 0.2s ease-in-out, opacity 0.2s linear";
        tout.style.left = "5em";
        tout.style.opacity = 0;
        
        
        tin.style.transition = "left 0.2s ease-in-out, opacity 0.2s linear";
        tin.style.left = "2em";
        tin.style.opacity = 1;

        if (currScrn == 'nam'){
            
            backbtn.style.transition = "left 0.2s ease-in-out, opacity 0.2s linear";
            backbtn.style.left = "4em";
            backbtn.style.opacity = 0;
        }
    }, 0);

    setTimeout(function() {
        tout.style.display = 'none';
        if (currScrn == 'nam'){
            backbtn.style.display = 'none';
        }
    }, 400);

    
}



function onMouseMove(event) {
    event.preventDefault();

    mouse.x = ( event.clientX / window.innerWidth ) * 2 - 1;
    mouse.y = - ( event.clientY / window.innerHeight ) * 2 + 1;

    raycaster.setFromCamera(mouse, camera);

    var intersects = raycaster.intersectObjects(scene.children, true);
    for (var i = 0; i < intersects.length; i++) {
        this.tl = new TimelineMax();
        this.tl.to(intersects[i].object.scale, 1, {x: 2, ease: Expo.easeOut})
        this.tl.to(intersects[i].object.scale, .5, {x: .5, ease: Expo.easeOut})
        this.tl.to(intersects[i].object.position, .5, {x: 2, ease: Expo.easeOut})
        this.tl.to(intersects[i].object.rotation, .5, {y: Math.PI*.5, ease: Expo.easeOut}, "=-1.5")
    }
}



window.addEventListener('mousemove', onMouseMove);
render();