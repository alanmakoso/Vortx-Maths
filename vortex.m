function [] = vortex(multiplier, modulo)
%UNTITLED2 Summary of this function goes here
%Detailed explanation goes here
    
    %Creates blank array of size modulo
    arrBlank = zeros(modulo);
    array = arrBlank(1,:);
    clear arrBlank

    %Graphs unit circle of radius 1
    %splits it into modulo sections: {x = sin(theta); y = cos(theta)}
        %theta is central angle from vertical, changes by 2pi/modulo
    x_circ = -1:0.01:1;
    plot(x_circ, sqrt(1 - x_circ.^2), '-k'); %circle cartesisan equation
    hold on
    plot(x_circ, -1 .* sqrt(1 - x_circ.^2), '-k'); %circle cartesian eq.
    hold on
    axis equal
    axis off
    
    for theta = 0:(2*pi/modulo):2*pi
        scatter(sin(theta), cos(theta), 'black', 'filled');
    end
    hold on

    %Assign multiplier values to the array (of length modulo)
    for i = 0:length(array)
        array(i+1) = multiplier .^ i;
    end
    disp(array);
    
    %Modulo each array (of length modulo) value by modulo
    for j = 0:length(array)-1
        array(j+1) = mod(array(j+1), modulo);
    end
    disp(array);
    
    %Cuts array when the sequence repeats
    arrCut = [1]; % Always start at 1
    for k = 2:length(array)
        arrCut(k) = array(k);
        if (array(k) == array(1))
            break;
        end
    end
    disp(arrCut);
    
    %Connecting the dots using the pattern
    %Iterate through arrCut; multiply central angle by arrCut index value;
    %Plot segments parametrically using {x = sin(angle); y = cos(angle)}
    hold on
    for n = 1:length(arrCut)-1
        plot([sin( arrCut(n) * (2*pi/modulo) ), ...
              sin( arrCut(n+1) * (2*pi/modulo) )], ...
             [cos( arrCut(n) * (2*pi/modulo) ), ...
              cos( arrCut(n+1) * (2*pi/modulo) )],'-r');
    end
end